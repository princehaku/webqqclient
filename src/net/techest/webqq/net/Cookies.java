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
 *  Created on : 2011-8-31, 下午6:41:28
 *  Author     : princehaku
 */
package net.techest.webqq.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 参数类 方便吧参数组合成字符串 warning 没有经过urlencode
 *
 * @author princehaku
 *
 */
public class Cookies extends HashMap<String, String> {

    @Override
    public java.lang.String toString() {
        Iterator<Entry<String, String>> ir = this.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (ir.hasNext()) {
            Entry<String, String> obj = ir.next();
            sb.append(obj.getKey());
            sb.append("=");
            sb.append(obj.getValue());
            if (ir.hasNext()) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public Iterator<Entry<String, String>> getIterator() {
        return this.entrySet().iterator();
    }
}
