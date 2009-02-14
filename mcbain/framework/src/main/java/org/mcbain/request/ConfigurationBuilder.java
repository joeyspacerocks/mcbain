// Copyright 2007 Joe Trewin
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.mcbain.request;


public class ConfigurationBuilder {

	private Context context;
	private ControllerChain currentControllerChain;

	public ConfigurationBuilder(Context context) {
		this.context = context;
	}

	public ConfigurationBuilder route(String path) {
		currentControllerChain = new ControllerChain();
		context.router().add(path, currentControllerChain);
		return this;
	}

	public ConfigurationBuilder to(Controller controller) {
		currentControllerChain.controller(controller);
		return this;
	}

	public ConfigurationBuilder via(Interceptor interceptor) {
		currentControllerChain.addInterceptor(interceptor);
		return this;
	}
}
