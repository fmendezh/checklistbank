/*
 * Copyright 2011 Global Biodiversity Information Facility (GBIF)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.checklistbank.ws.client.guice;

import org.gbif.api.service.checklistbank.NameUsageSearchService;
import org.gbif.checklistbank.ws.client.NameUsageSearchWsClient;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Guice module that includes an implementation for the ChecklistBank NameUsageSearchService.
 * It required the ChecklistBankHttpClient annotated jersey client and should not be used on its own.
 * Use the main #ChecklistBankWsClientModule(true,false) instead with its configurable constructor.
 */
public class ChecklistBankWsSearchClientModule extends AbstractModule {

  /**
   * Use the main #ChecklistBankWsClientModule(true,false) instead.
   */
  protected ChecklistBankWsSearchClientModule() {
  }

  @Override
  protected void configure() {
    bind(NameUsageSearchService.class).to(NameUsageSearchWsClient.class).in(Scopes.SINGLETON);
  }

  @Provides
  @Singleton
  @ChecklistBankSearchWs
  public WebResource providesChecklistBankUsageSearchWsWebResource(Client client,
    @Named("checklistbank.search.ws.url") String url) {
    return client.resource(url);
  }


}
