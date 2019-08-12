/**
* Copyright 2015 IBM Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.sample;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.ibm.mfp.adapter.api.ConfigurationAPI;
import com.ibm.mfp.adapter.api.MFPJAXRSApplication;
import org.lightcouch.CouchDbException;

import javax.ws.rs.core.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CloudantJavaApplication extends MFPJAXRSApplication{

	static Logger logger = Logger.getLogger(CloudantJavaApplication.class.getName());

	@Context
	ConfigurationAPI configurationAPI;

	public Database db = null;

	protected void init() throws Exception {
		logger.warning(this.getClass()+ " adapter initialized!");
	}

	public void initConnection() {
		String cloudantDBName = configurationAPI.getPropertyValue("DBName");
		String cloudantAccount = configurationAPI.getPropertyValue("account");
		String cloudantKey = configurationAPI.getPropertyValue("key");
		String cloudantPassword = configurationAPI.getPropertyValue("password");

		if (!cloudantDBName.isEmpty() && !cloudantAccount.isEmpty() && !cloudantKey.isEmpty() && !cloudantPassword.isEmpty()){
			try {
				CloudantClient cloudantClient = new CloudantClient(cloudantAccount,cloudantKey,cloudantPassword);
				db = cloudantClient.database(cloudantDBName, false);
			} catch (CouchDbException e) {
				System.out.println(e.getMessage());
				
			}
		}
	}

	protected void destroy() throws Exception {
		logger.info("Adapter destroyed!");
	}

	protected String getPackageToScan() {
		// The package of this class will be scanned (recursively) to find JAX-RS
		// resources.
		// It is also possible to override "getPackagesToScan" method in order to return
		// more than one package for scanning
		return getClass().getPackage().getName();
	}
}
