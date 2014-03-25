package net.vdrinkup.alpaca.context;

/**
 * 上下文状态
 * <pre>
 * 上下文包括如下状态：无效、有效、异常与终止四个状态。
 * 1.上下文只有在被标识为有效的状态下，才能被各个环节使用。
 * 2.如果被标识为无效状态，则不会被各个环节使用，会被忽略并传递到下一个环节。
 *   系统自检时，可将自检用的上下文设置成无效状态。
 * 3.异常状态是当执行出现异常时，上下文对象持有的状态。处于异常状态的上下文会被传递给异常处理程序。
 *   与无效状态的区别在于，处于异常状态的上下文对象不会继续执行后续环节并触发相关异常处理程序。
 * 4.终止状态用于终止后续流程，不会触发其他流程。
 * </pre>
 * @author liubing
 * Date 2013-11-11
 */
public enum ContextStatus {
	/**
	 * 无效状态
	 */
	INVALID( -1 ),
	/**
	 * 有效状态
	 */
	VALID( 1 ),
	/**
	 * 异常状态
	 */
	EXCEPTION( -2 ), 
	/**
	 * 终止状态
	 */
	TERMINATED( 0 );
	
	ContextStatus( int status ) {
		this.status = status;
	}
	
	private int status;
	
	public int getStatus() {
		return status;
	}

}
