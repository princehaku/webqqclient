/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : 2011-9-4, 下午6:03:40
 *  Author     : princehaku
 */

package net.techest.webqq.bean;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
/**验证码图片
 * 
 * @author princehaku
 *
 */
public class VerifyImage implements IFileProcess{
	/**唯一标识符 一般说来是用户的qq号码
	 * 
	 */
	private String uin;
	/**应用的id
	 * 
	 */
	private String aid;
	/**图片信息
	 * 
	 */
	private ImageIcon image;
	/**原始数据
	 * 
	 */
	private byte[] content;
	
	public VerifyImage(byte[] content){
		this.content=content;
		this.image=new ImageIcon(content);
	}
	
	/**保存到某个位置
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void saveTo(String path) throws IOException{
		FileOutputStream fops = new FileOutputStream(path); 
		fops.write(this.content);
		fops.close();
	}
	
	public ImageIcon getImage(){
		return this.image;
	}
	
	public byte[] getImageData(){
		return this.content;
	}

}
