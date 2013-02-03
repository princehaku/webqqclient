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
 *  Created on : 2011-9-5, 上午11:57:51
 *  Author     : princehaku
 */
package net.techest.webqq.bean.api;

import java.util.HashMap;

/**
 * 虚基类 API工厂
 *
 * @author haku
 *
 */
public abstract class APIFacroty {

    protected HashMap<String, APIBase> apis = new HashMap<String, APIBase>();

    /**
     * 根据名称得到一个API 注意这个api每次得到的都不一样 都是从注册的池里面的新的实例
     *
     * @param name
     * @return
     */
    public APIBase getApiByName(String name) throws Exception {
        APIBase api = apis.get(name);
        APIBase newApi = api.getClass().newInstance();
        return newApi;
    }

    public void registerApi(APIBase api) {
        apis.put(api.getApiName(), api);
    }

    public void setApis(HashMap<String, APIBase> apis) {
        this.apis = apis;
    }

    public void removeApi(APIBase api) {
        apis.remove(api.getApiName());
    }

    public void removeApiByName(String name) {
        apis.remove(name);
    }
}
