package org.gbif.nub.build;

import org.gbif.api.model.common.paging.Pageable;
import org.gbif.api.model.common.paging.PagingResponse;
import org.gbif.api.model.common.search.SearchResponse;
import org.gbif.api.model.registry.Comment;
import org.gbif.api.model.registry.Contact;
import org.gbif.api.model.registry.Dataset;
import org.gbif.api.model.registry.Endpoint;
import org.gbif.api.model.registry.Identifier;
import org.gbif.api.model.registry.MachineTag;
import org.gbif.api.model.registry.Metadata;
import org.gbif.api.model.registry.Network;
import org.gbif.api.model.registry.Tag;
import org.gbif.api.model.registry.search.DatasetSearchParameter;
import org.gbif.api.model.registry.search.DatasetSearchRequest;
import org.gbif.api.model.registry.search.DatasetSearchResult;
import org.gbif.api.model.registry.search.DatasetSuggestRequest;
import org.gbif.api.service.registry.DatasetSearchService;
import org.gbif.api.service.registry.DatasetService;
import org.gbif.api.vocabulary.Country;
import org.gbif.api.vocabulary.DatasetType;
import org.gbif.api.vocabulary.IdentifierType;
import org.gbif.api.vocabulary.MetadataType;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;

public class DatasetMockService implements DatasetSearchService, DatasetService {
  private List<UUID> keys;

  @Override
  public SearchResponse<DatasetSearchResult, DatasetSearchParameter> search(DatasetSearchRequest searchRequest) {
    SearchResponse<DatasetSearchResult, DatasetSearchParameter> resp = new SearchResponse<DatasetSearchResult, DatasetSearchParameter>(searchRequest);
    List<DatasetSearchResult> results = Lists.newArrayList();
    for (UUID k : keys) {
      DatasetSearchResult d = new DatasetSearchResult();
      d.setKey(k);
      d.setTitle("Dataset " + k.toString());
      d.setType(DatasetType.CHECKLIST);
      results.add(d);
    }
    resp.setResults(results);
    return resp;
  }


  /**
   * Sets the keys of the datasets to be returned with the search method.
   * @param keys
   */
  public void setKeys(UUID ... keys) {
    this.keys = Lists.newArrayList(keys);
  }

  @Override
  public Dataset get(@NotNull UUID key) {
    Dataset d = new Dataset();
    d.setKey(key);
    d.setTitle("Dataset " + key.toString());
    d.setType(DatasetType.CHECKLIST);
    if (keys.contains(key)) {
      MachineTag mt = new MachineTag();
      mt.setNamespace(NubGenerator.MT_NAMESPACE);
      mt.setName(NubGenerator.MT_NUB_PRIORITY_NAME);
      mt.setValue(String.format("%04d", keys.indexOf(key)));
      d.getMachineTags().add(mt);
    }
    return d;
  }



  @Override
  public List<DatasetSearchResult> suggest(DatasetSuggestRequest suggestRequest) {
    throw new UnsupportedOperationException("Not implemented yet");
  }


  @Override
  public PagingResponse<Dataset> listConstituents(UUID datasetKey, @Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listByCountry(@NotNull Country country, @Nullable DatasetType type,
    @Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listByType(DatasetType datasetType, @Nullable Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Metadata> listMetadata(UUID datasetKey, @Nullable MetadataType type) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Metadata getMetadata(int metadataKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteMetadata(int metadataKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Metadata insertMetadata(UUID datasetKey, InputStream document) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public InputStream getMetadataDocument(UUID datasetKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public InputStream getMetadataDocument(int metadataKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listDeleted(@Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listDuplicates(@Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listDatasetsWithNoEndpoint(@Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public UUID create(@NotNull Dataset entity) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void delete(@NotNull UUID key) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> list(@Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> search(String query, @Nullable Pageable page) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listByIdentifier(IdentifierType identifierType, String s,
    @Nullable Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listByIdentifier(String s, @Nullable Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void update(@NotNull Dataset entity) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addComment(@NotNull UUID targetEntityKey, @NotNull Comment comment) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteComment(@NotNull UUID targetEntityKey, int commentKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Comment> listComments(@NotNull UUID targetEntityKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addContact(@NotNull UUID targetEntityKey, @NotNull Contact contact) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteContact(@NotNull UUID targetEntityKey, int contactKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Contact> listContacts(@NotNull UUID targetEntityKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void updateContact(@NotNull UUID targetEntityKey, @NotNull Contact contact) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addEndpoint(@NotNull UUID targetEntityKey, @NotNull Endpoint endpoint) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteEndpoint(@NotNull UUID targetEntityKey, int endpointKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Endpoint> listEndpoints(@NotNull UUID targetEntityKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addIdentifier(@NotNull UUID targetEntityKey, @NotNull Identifier identifier) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteIdentifier(@NotNull UUID targetEntityKey, int identifierKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Identifier> listIdentifiers(@NotNull UUID targetEntityKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addMachineTag(@NotNull UUID targetEntityKey, @NotNull MachineTag machineTag) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addMachineTag(@NotNull UUID targetEntityKey, @NotNull String namespace, @NotNull String name,
    @NotNull String value) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteMachineTag(@NotNull UUID targetEntityKey, int machineTagKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteMachineTags(@NotNull UUID targetEntityKey, @NotNull String namespace) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteMachineTags(@NotNull UUID targetEntityKey, @NotNull String namespace, @NotNull String name) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<MachineTag> listMachineTags(@NotNull UUID targetEntityKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addTag(@NotNull UUID targetEntityKey, @NotNull String value) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int addTag(@NotNull UUID targetEntityKey, @NotNull Tag tag) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void deleteTag(@NotNull UUID taggedEntityKey, int tagKey) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Tag> listTags(@NotNull UUID taggedEntityKey, @Nullable String owner) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Network> listNetworks(@NotNull UUID uuid) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public PagingResponse<Dataset> listConstituents(@Nullable Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
