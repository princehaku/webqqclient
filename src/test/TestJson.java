package test;

import net.sf.json.JSONObject;

import java.io.IOException;

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
 *  Created on : 2011-9-5, 下午12:50:39
 *  Author     : princehaku
 */
public class TestJson {

    public static void main(String[] args) throws IOException {
        String res = new String("{\"retcode\":103,\"errmsg\":\"\"}");
        JSONObject jsonObject = JSONObject.fromObject(res);
        System.out.println(jsonObject.getString("retcode"));
    }
}
