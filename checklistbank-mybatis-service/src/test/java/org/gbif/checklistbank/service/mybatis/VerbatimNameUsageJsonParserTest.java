package org.gbif.checklistbank.service.mybatis;

import org.gbif.api.model.checklistbank.VerbatimNameUsage;
import org.gbif.api.vocabulary.Extension;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VerbatimNameUsageJsonParserTest {

  @Test
  public void testJsonToVerbatim() throws Exception {
    final String json = "{\"id\":\"100\",\n" + " \"taxonomicStatus\":\"valid\",\n" + " \"taxonRank\":\"Species\",\n"
                        + " \"scientificNameAuthorship\":null,\n" + " \"parentNameUsageID\":\"86\",\n"
                        + " \"acceptedNameUsageID\":null,\n" + " \"scientificName\":\"Spirillum beijerinckii\",\n"
                        + " \"extensions\": {\n"
                        + "   \"VernacularName\" : [{\"vernacularName\":\"Strand\", \"language\":\"en\"}, {\"vernacularName\":\"Bönse\", \"language\":\"se\"}],\n"
                        + "   \"Distribution\" : [{\"hi\":\"ho\"}]\n" + " }}";
    VerbatimNameUsageJsonParser parser = new VerbatimNameUsageJsonParser();
    VerbatimNameUsage v = parser.jsonToVerbatim(json);

    assertEquals("100", v.getFields().get("id"));
    assertEquals("valid", v.getFields().get("taxonomicStatus"));
    assertEquals("Spirillum beijerinckii", v.getFields().get("scientificName"));
    assertEquals("86", v.getFields().get("parentNameUsageID"));
    assertEquals(2, v.getExtensions().get(Extension.VERNACULAR_NAME).size());
    assertEquals(1, v.getExtensions().get(Extension.DISTRIBUTION).size());
  }
}
