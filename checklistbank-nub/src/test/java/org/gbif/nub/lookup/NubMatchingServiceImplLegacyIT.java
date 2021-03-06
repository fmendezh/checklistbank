package org.gbif.nub.lookup;

import org.gbif.api.model.checklistbank.NameUsage;
import org.gbif.api.model.checklistbank.NameUsageMatch;
import org.gbif.api.model.common.LinneanClassification;
import org.gbif.nameparser.NameParser;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NubMatchingServiceImplLegacyIT {

  private static NubMatchingServiceImpl matcher;
  private static List<NameUsage> names;

  @BeforeClass
  public static void buildMatcher() throws IOException {
    HigherTaxaLookup syn = new HigherTaxaLookup();
    syn.loadClasspathDicts("dicts");

    names = NubIndexTest.readTestNames();

    NubIndex index = new NubIndex();
    for (NameUsage u : names) {
      index.addNameUsage(u);
    }
    matcher = new NubMatchingServiceImpl(index, syn, new NameParser());
  }

  /**
   * trying all we can to produce a NPE ...
   */
  @Test
  public void testRatingNPE() throws IOException {
    LinneanClassification cl = new NameUsageMatch();
    cl.setKingdom(null);
    cl.setPhylum(".");
    matcher.match("---", null, cl, false, 50, true);

    // blacklisted names turn into nulls via the synonym lookup!
    cl = new NameUsageMatch();
    cl.setKingdom(null);
    cl.setPhylum("Unknown");
    matcher.match("FAMILY", null, cl, true, 50, true);
  }

   @Test
   public void testNoMatch() throws IOException {
     LinneanClassification cl = new NameUsageMatch();
     NameUsageMatch m = matcher.match("", null, cl, false, 50, true);
     assertEquals(NameUsageMatch.MatchType.NONE, m.getMatchType());
   }

  @Test
  public void testClassificationSimilarity() throws IOException {

    LinneanClassification cn1 = new NameUsageMatch();
    LinneanClassification cn2 = new NameUsageMatch();

    int score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score > -10);
    assertTrue(score < 0);

    cn1.setFamily("Asteraceae");
    cn2.setFamily("Asteraceae");
    score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score > 10);

    cn2.setFamily("Asteraceaee");
    score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score <= -5);

    cn1.setKingdom("A");
    cn2.setKingdom("B");
    score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score < -12);
  }

  /**
   * compare
   * Plantae;Dinophyta;Dinophyceae;Gonyaulacales;;;
   * with
   * Protozoa;Dinophyta;;;;;
   *
   * @throws java.io.IOException
   */
  @Test
  public void testRatingWihtKingdomSynonym() throws IOException {

    LinneanClassification cn1 = new NameUsageMatch();
    cn1.setKingdom("Plantae");
    cn1.setPhylum("Dinophyta");
    cn1.setClazz("Dinophyceae");
    cn1.setOrder("Gonyaulacales");

    LinneanClassification cn2 = new NameUsageMatch();
    cn2.setKingdom("Plantae");
    cn2.setPhylum("Dinophyta");
    cn2.setClazz("Dinophyceae");
    cn2.setOrder("Gonyaulacales");

    // test identical
    int score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score > 45);


    // test kingdom synonym
    cn2.setKingdom("Plants");
    cn2.setPhylum("Dinophyta");
    cn2.setClazz("");
    cn2.setOrder("");

    // test identical
    score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score > 15);

    // test vague kingdom
    cn2.setKingdom("Protozoa");
    score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score > -10);
    assertTrue(score <= 0);

    // test very different kingdom
    cn2.setKingdom("Animalia");
    score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score <= -15);
  }

  /**
   * Plantae	Rhodophyta	Amphibia rodophyta
   */
  @Test
  public void testAmphibiaRating() throws IOException {

    LinneanClassification cn1 = new NameUsageMatch();
    cn1.setKingdom("Plantae");
    cn1.setPhylum("Rhodophyta");

    LinneanClassification cn2 = new NameUsageMatch();
    cn2.setKingdom("Plantae");
    cn2.setPhylum("Rhodophyta");

    // test identical
    int score = matcher.classificationSimilarity(cn1, cn2);
    assertTrue(score > 16);

  }

  private void assertMatchedUsageKey(String name, LinneanClassification query, Integer expectedKey) {
    NameUsageMatch best = matcher.match(name, null, query, false, true);
    assertEquals( expectedKey, best.getUsageKey());
  }

  @Test
  public void testRating() throws IOException {
    final String queryName = "Acanthophora";

    LinneanClassification query = new NameUsageMatch();
    query.setKingdom("Animalia");
    query.setPhylum("Porifera");
    assertMatchedUsageKey(queryName, query, 3);

    query.setPhylum("Arthropoda");
    query.setClazz("Insecta");
    assertMatchedUsageKey(queryName, query, 2);

    query.setOrder("Wrongly");
    assertMatchedUsageKey(queryName, query, 2);

    query.setFamily("Geometridae");
    assertMatchedUsageKey(queryName, query, 1);


    query = new NameUsageMatch();
    query.setFamily("Rhodomelaceae");
    assertMatchedUsageKey(queryName, query, 5);

    query.setFamily("Araliaceae");
    assertMatchedUsageKey(queryName, query, 4);

    query.setFamily("PHORIdae");
    assertMatchedUsageKey(queryName, query, 2);


    // homonym matching without a classification should return a NONE match
    query = new NameUsageMatch();
    query.setKingdom("");
    query.setPhylum("");
    query.setClazz("");
    query.setOrder("");
    query.setFamily("");
    query.setGenus("");
    NameUsageMatch best = matcher.match(queryName, null, query, false, true);
    assertEquals(NameUsageMatch.MatchType.NONE, best.getMatchType());

    // test that without a clear classification the best match is empty
    query = new NameUsageMatch();
    query.setKingdom("Animalia");
    query.setGenus("Acanthophora");
    best = matcher.match(queryName, null, query, false, true);
    assertEquals(NameUsageMatch.MatchType.NONE, best.getMatchType());
  }

  @Test
  public void testFuzzyMacthing() throws IOException {
    LinneanClassification query = new NameUsageMatch();
    query.setKingdom("Animalia");
    query.setPhylum("Porifera");
    assertMatchedUsageKey("Acanthophora", query, 3);
    assertMatchedUsageKey("Acantopora", query, 3);
    assertMatchedUsageKey("Akantophora", query, 3);
    assertMatchedUsageKey("Acantoofora", query, 3);
  }

}
