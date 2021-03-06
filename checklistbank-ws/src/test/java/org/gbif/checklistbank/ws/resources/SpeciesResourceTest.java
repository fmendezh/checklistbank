package org.gbif.checklistbank.ws.resources;

import org.gbif.api.model.checklistbank.NameUsage;
import org.gbif.api.model.checklistbank.TypeSpecimen;
import org.gbif.api.model.common.paging.Pageable;
import org.gbif.api.model.common.paging.PagingRequest;
import org.gbif.api.model.common.paging.PagingResponse;
import org.gbif.api.service.checklistbank.DescriptionService;
import org.gbif.api.service.checklistbank.DistributionService;
import org.gbif.api.service.checklistbank.ImageService;
import org.gbif.api.service.checklistbank.NameUsageService;
import org.gbif.api.service.checklistbank.ReferenceService;
import org.gbif.api.service.checklistbank.SpeciesProfileService;
import org.gbif.api.service.checklistbank.TypeSpecimenService;
import org.gbif.api.service.checklistbank.VernacularNameService;
import org.gbif.api.vocabulary.TypeStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpeciesResourceTest {

  private NameUsageService mockNameUsageService;
  private VernacularNameService mockVernacularNameService;
  private TypeSpecimenService mockTypeSpecimenService;
  private SpeciesProfileService mockSpeciesProfileService;
  private ReferenceService mockReferenceService;
  private ImageService mockImageService;
  private DescriptionService mockDescriptionService;
  private DistributionService mockDistributionService;

  private SpeciesResource resource;

  private NameUsage nameUsage;
  private Locale locale;
  private static final int NAME_USAGE_KEY = 103350120;
  private Pageable page;

  @Before
  public void setUp() throws Exception {
    mockNameUsageService = mock(NameUsageService.class);
    mockVernacularNameService = mock(VernacularNameService.class);
    mockTypeSpecimenService = mock(TypeSpecimenService.class);
    mockSpeciesProfileService = mock(SpeciesProfileService.class);
    mockReferenceService = mock(ReferenceService.class);
    mockImageService = mock(ImageService.class);
    mockDescriptionService = mock(DescriptionService.class);
    mockDistributionService = mock(DistributionService.class);

    resource = new SpeciesResource(mockNameUsageService, mockVernacularNameService, mockTypeSpecimenService,
      mockSpeciesProfileService, mockReferenceService, mockImageService, mockDescriptionService,
      mockDistributionService);

    locale = Locale.US;
    nameUsage = new NameUsage();
    nameUsage.setKey(NAME_USAGE_KEY);

    page = new PagingRequest(0, 1);
  }

  @Test
  public void testGet() throws Exception {
    when(mockNameUsageService.get(NAME_USAGE_KEY, locale)).thenReturn(nameUsage);
    NameUsage nameUsage = resource.get(NAME_USAGE_KEY, locale);
    assertEquals(String.valueOf(NAME_USAGE_KEY), nameUsage.getKey().toString());
  }

  @Test
  public void testListTypeSpecimensByNameUsage() {
    TypeSpecimen ts = new TypeSpecimen();
    ts.setUsageKey(NAME_USAGE_KEY);
    ts.setTypeStatus(TypeStatus.PARATYPE);
    List<TypeSpecimen> list = new LinkedList<TypeSpecimen>();
    list.add(ts);
    PagingResponse<TypeSpecimen> response = new PagingResponse<TypeSpecimen>(0, 1, 1l, list);

    when(mockTypeSpecimenService.listByUsage(NAME_USAGE_KEY, page)).thenReturn(response);

    PagingResponse<TypeSpecimen> result = resource.listTypeSpecimensByNameUsage(NAME_USAGE_KEY, page);
    assertEquals(result.getResults().get(0).getUsageKey().toString(), String.valueOf(NAME_USAGE_KEY));
  }

}
