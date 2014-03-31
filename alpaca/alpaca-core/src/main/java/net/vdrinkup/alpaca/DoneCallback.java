/**
 * 
 */
package net.vdrinkup.alpaca;

/**
 * 完成回调接口
 * <p>
 * 该接口用于异步执行完成后的回调操作
 * </p>
 * @author pluto.bing.liu
 */
public interface DoneCallback {
	
	public void done( boolean done );

}
