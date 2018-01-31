/**
 * 
 */
package com.xinguang.xherald.mq.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author hzlige
 *
 */
public class Exchange  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public class Message_stats  implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public class Details  implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public int rate;
		}
		public int confirm;
		public Details confirm_details;
		public int publish_in;
		public Details publish_in_details;
		public int publish_out;
		public Details publish_out_details;
	}
	public Message_stats message_stats;
	public String name;
	public String vhost;
	public String type;
	public boolean durable;
	public boolean auto_delete;
	public boolean internal;
	public List<String> arguments;
}
