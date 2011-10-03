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

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
/**图片类
 * 
 * @author princehaku
 *
 */
public class Image implements IFileProcess{
	protected ImageIcon image;
	/**原始数据
	 * 
	 */
	protected byte[] content;
	/**通过字节流构造一个图片
	 * 
	 * @param content
	 */
	public Image(byte[] content){
		this.content=content;
		this.image=new ImageIcon(content);
	}
	/**使用一个imageIcon来构造图片
	 * 注意不能保存 因为 byte[]没有转换
	 * @param imageIcon
	 */
	public Image(ImageIcon imageIcon) {
		this.content=new byte[1];
		this.image=imageIcon;
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
