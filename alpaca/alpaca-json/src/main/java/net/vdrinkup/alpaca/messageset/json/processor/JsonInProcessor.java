/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.JsonConstants;
import net.vdrinkup.alpaca.messageset.json.definition.JsonInDefinition;


/**
 * JSO格式报文输入配置处理类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-27
 */
public class JsonInProcessor extends AbstractProcessor< JsonInDefinition > {


	public JsonInProcessor( JsonInDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		if ( ! ( context.getIn() instanceof byte[] ) ) {
			throw new IllegalArgumentException( "The in of DataContext must be an instance of byte[]" );
		}
		ByteBuffer buffer = ByteBuffer.wrap( ( byte[] ) context.getIn() );
		context.setIn( buffer );
		context.setProperty( "parseKey", new ByteArrayOutputStream() );
		context.setProperty( "definition", getDefinition() );
		try {
			decode( context );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "The sdo is [{}]", context.getOut().toString() );
		}
		return true;
	}
	
	private void decode( DataContext context ) throws Exception {
		int n;
		final List< Object > stack = new LinkedList< Object >();
		final List< byte[] > keyStack = new LinkedList< byte[] >();
		final List< MessageNode > defStack = new LinkedList< MessageNode >();
		Object target = context.getOut();
		MessageNode definition = context.getProperty( "definition", MessageNode.class );
		final ByteArrayOutputStream curBuff = context.getProperty( "parseKey", ByteArrayOutputStream.class );
		final ByteBuffer buffer = context.getIn();
		while ( buffer.position() != buffer.capacity() ) {
			n = buffer.get();
			if ( n == JsonConstants.WHITESPACE || n == JsonConstants.CR || n == JsonConstants.LF ) {
				//当前字符是空白字符，包括空格、回车、换行等，则忽略
				continue ;
			} else if ( buffer.position() == 1 && ! ( n == JsonConstants.L_BRACES || n == JsonConstants.L_BRACKET ) ) {
				//起始字符不是左大括号或者左中括号，则抛出异常
				throw new RuntimeException( "The stream of json must be begin with '{' or '['" );
			} else if ( n == JsonConstants.L_BRACES ) {
				//当前字符时左大括号，则将当前值对象入栈并新的值对象
				if ( target != null ) {
					stack.add( 0, target );
				}
				target = DataFactory.INSTANCE.create();
				if ( keyStack.size() > 0 ) {
					defStack.add( 0, definition );
					definition = definition.findSub( new String( keyStack.get( 0 ) ) );
					context.setProperty( "definition", definition );
				}
			} else if ( n == JsonConstants.L_BRACKET ) {
				//当前字符是左中括号，开始处理数组类型值
				int position = buffer.position();
				buffer.position( position - 1 );
				if ( keyStack.size() > 0 ) {
					defStack.add( 0, definition );
					definition = definition.findSub( new String( keyStack.remove( 0 ) ) );
					context.setProperty( "definition", definition );
				}
				Object obj = parseArray( context );
				
				if ( position == 1 ) {
					target = obj;
				} else {
					context.setIn( obj );
					context.setOut( target );
					( ( ProcessorDefinition ) definition ).createProcessor().process( context );
					context.setIn( buffer );
					context.setOut( null );					
				}
				if ( defStack.size() > 0 ) {
					definition = defStack.remove( 0 );
					context.setProperty( "definition", definition );
				}
				position = buffer.position();
				buffer.position( position - 1 );
			} else if ( n == JsonConstants.R_BRACES ) {
				//当前字符为右大括号
				if ( curBuff.size() > 0 ) {
					final String nodeName = new String( keyStack.remove( 0 ) );
					MessageNode node = definition.findSub( nodeName );
					if ( node == null ) {
						LOG.warn( "Can not found MessageNode named [{}].", nodeName );
					} else {
						context.setIn( curBuff.toByteArray() );
						context.setOut( target );
						( ( ProcessorDefinition ) node ).createProcessor().process( context );
						context.setIn( buffer );
						context.setOut( null );	
					}
				}
				curBuff.reset();
				if ( stack.size() != 0 ) {
					context.setIn( target );
					target = stack.remove( 0 );
					context.setOut( target );
					if ( defStack.size() != 0 ) {
						definition = defStack.remove( 0 );
					}
					final String nodeName = new String( keyStack.remove( 0 ) );
					MessageNode node = definition.findSub( nodeName );
					if ( node == null ) {
						LOG.warn( "Can not found MessageNode named [{}].", nodeName );
					} else {
						( ( ProcessorDefinition ) node ).createProcessor().process( context );
						context.setIn( buffer );
					}
				} else {
					break ;
				}
			} else if ( n == JsonConstants.QUOTATION_MARK || n == JsonConstants.D_QUOTATION_MARK ) {
				continue ;
			} else if ( n == JsonConstants.COMMA ) {
				//当前字节是“,”且当前key不为null
				if ( keyStack.size() > 0 ) { 
					final String nodeName = new String( keyStack.remove( 0 ) );
					MessageNode node = definition.findSub( nodeName );
					if ( node == null ) {
						LOG.warn( "Can not found MessageNode named [{}].", nodeName );						
					} else {
						context.setIn( curBuff.toByteArray() );
						context.setOut( target );
						( ( ProcessorDefinition ) node ).createProcessor().process( context );
						context.setIn( buffer );
						context.setOut( null );
					}
				}
				curBuff.reset();
			} else if ( n == JsonConstants.COLON ) {
				//当前字节是“:”,设置Key值，缓存复位
				int position = buffer.position();
				buffer.position( position - 2 );
				n = buffer.get();
				if ( n == JsonConstants.D_QUOTATION_MARK || n == JsonConstants.D_QUOTATION_MARK ) {
					keyStack.add( 0, curBuff.toByteArray() );
					curBuff.reset();
				}
				buffer.position( position );
			} else if ( n == JsonConstants.R_BRACKET ) {
				//当前字符是右中括号
				continue ;
			} else if ( n == JsonConstants.BACKSLASH ) {
				continue;
			} else {
				curBuff.write( n );
			}
		}
		context.setOut( target );
	}
	
	private Object parseArray( DataContext context ) throws Exception {
		int n;
		int pre = 0;
		List< Object > target = null;
		List< List< Object > > stack = new LinkedList< List< Object > >();
		Object curVar = null;
		ByteArrayOutputStream curBuff = new ByteArrayOutputStream();
		ByteBuffer buffer = context.getIn();
		while ( buffer.position() != buffer.capacity() ) {
			n = buffer.get();
			process : if ( n == JsonConstants.WHITESPACE || n == JsonConstants.CR || n == JsonConstants.LF ) {
				break process;
			} else if ( buffer.position() == 0 && n != JsonConstants.L_BRACKET ) {
				throw new RuntimeException( "The input stream must begin with '['." );
			} else if ( n == JsonConstants.L_BRACKET ) {
				if ( target != null ) {
					stack.add( 0, target );
				}
				target = new LinkedList< Object >();
			} else if ( n == JsonConstants.R_BRACKET ) {
				if ( pre != JsonConstants.R_BRACES && pre != JsonConstants.R_BRACKET ) {
					//如果前一字节不是'}'，则保存之前的值后将临时缓存复位					
					curVar = new String( curBuff.toByteArray() );
					target.add( curVar );
					curBuff.reset();
					curVar = null;
				}
				if ( stack.size() != 0 ) {
					List< Object > curTarget = target;
					target = stack.remove( 0 );
					target.add( curTarget );
				} else {
					break ;
				}
			} else if ( n == JsonConstants.COMMA ) {
				if ( pre != JsonConstants.R_BRACKET && pre != JsonConstants.R_BRACES ) {
					//如果前一字节即不是']'也不是'}'，则保存之前的值后将临时缓存复位
					curVar = new String( curBuff.toByteArray() );
					target.add( curVar );
					curBuff.reset();
					curVar = null;
				}
			} else if ( n == JsonConstants.QUOTATION_MARK || n == JsonConstants.D_QUOTATION_MARK ) {
				break process;
			} else if ( n == JsonConstants.L_BRACES ) {
				int position = buffer.position();
				buffer.position( position - 1 );
				decode( context );
				target.add( context.getOut() );
				position = buffer.position();
				buffer.position( position - 1 );
			} else if ( n == JsonConstants.R_BRACES ) {
				break process;
			} else {
				curBuff.write( n );
			}
			pre = n;
		}
		return target;
	}

}
