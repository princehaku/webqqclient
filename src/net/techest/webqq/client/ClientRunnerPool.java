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
 *  Created on : 2011-9-5, 上午8:38:47
 *  Author     : princehaku
 */
package net.techest.webqq.client;

import net.techest.util.Log4j;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.client.dialog.ServerDialog;

import java.util.HashMap;

/**
 * 用户运行池 负责维护多个用户的运行 但是每个用户只能有一个实例
 *
 * @author princehaku
 *
 */
public class ClientRunnerPool extends Thread {

    HashMap<String, ServerDialog> pool = new HashMap<String, ServerDialog>();

    public void addUser(QQUser user) {
        if (pool.get(user.getUin()) != null) {
            Log4j.getInstance().warn("已经存在此实例");
            return;
        }
        ServerDialog sd = new ServerDialog(user);
        sd.start();
        pool.put(user.getUin(), sd);
    }

    public ServerDialog getServerDialog(QQUser user) {
        return pool.get(user.getUin());
    }
}
