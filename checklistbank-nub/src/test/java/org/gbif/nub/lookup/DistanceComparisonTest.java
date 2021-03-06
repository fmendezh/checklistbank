package org.gbif.nub.lookup;

import org.gbif.nub.lookup.similarity.DamerauLevenshtein;
import org.gbif.nub.lookup.similarity.JaroWinklerSimilarity;
import org.gbif.nub.lookup.similarity.ModifiedDamerauLevenshtein;
import org.gbif.nub.lookup.similarity.ModifiedJaroWinklerSimilarity;
import org.gbif.nub.lookup.similarity.StringSimilarity;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistanceComparisonTest {
  private static final Logger LOG = LoggerFactory.getLogger(DistanceComparisonTest.class);

  private List<String[]> names = ImmutableList.of(
    new String[] {"Helga", "Markus"},
    new String[] {"Abies", "Apis"},
    new String[] {"Aneplus", "Anelus"},
    new String[] {"Aneplus vulgaris", "Anelus vulgaris"},
    new String[] {"Aneplus", "Anephlus"},
    new String[] {"Anelus", "Anephlus"},
    new String[] {"Abies alba", "Abies alba"},
    new String[] { "Apies alba", "Abies alba" },
    new String[] { "Apbies alba", "Abies alba" },
    new String[] { "Abbies alba", "Abies alba" },
    new String[] { "Obies alba", "Abies alba" },
    new String[] { "Abies allba", "Abies alba" },
    new String[] { "Abies ahlba", "Abies alba" },
    new String[] { "Abbies ahlba", "Abies alba" },
    new String[] { "Puma concolor", "Abies alba" },
    new String[] { "Pumac oncolor", "Puma concolor" },
    new String[] { "Pumaco color", "Puma concolor" },
    new String[] { "Pumae concolour", "Puma concolor" },
    new String[] { "Cnaemidophorus rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophorus rododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Caemidophorus rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnamidophorus rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophora rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophorhus rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemydophorus rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cneamidophorus rhododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophorus rododactyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophorus rhododatyla", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophorus rhododactula", "Cnaemidophorus rhododactyla" },
    new String[] { "Cnaemidophorus rhododactulla", "Cnaemidophorus rhododactyla" },

    new String[] { "Yigoga forcipula", "Yigoga forcipula" },
    new String[] { "Igoga forcipula", "Yigoga forcipula" },
    new String[] { "Yiogoga forcipula", "Yigoga forcipula" },
    new String[] { "Yigoga forzipula", "Yigoga forcipula" },

    new String[] { "Agrotis ripae", "Agrotis ripae" },
    new String[] { "Agrostis ripae", "Agrotis ripae" },
    new String[] { "Agrotis ripa", "Agrotis ripae" },
    new String[] { "Agrotis ripea", "Agrotis ripae" },

    new String[] { "Lasionycta leucocycla", "Lasionycta leucocycla" },
    new String[] { "Lasionicta leucocycla", "Lasionycta leucocycla" },
    new String[] { "Lasionykta leucocycla", "Lasionycta leucocycla" },
    new String[] { "Lasionycta leucocicla", "Lasionycta leucocycla" },

    new String[] { "Ophthalmis lincea", "Ophthalmis lincea" },
    new String[] { "Oftalmis lincea", "Ophthalmis lincea" },
    new String[] { "Ophthalmis linzea", "Ophthalmis lincea" }
  );

  @Test
  public void testGetSimilarity() throws Exception {
    for (String[] ns : names) {
      LOG.debug(ns[0] + "  x  " + ns[1]);
      doit("DL ", new DamerauLevenshtein(ns[0], ns[1]));
      doit("MDL", new ModifiedDamerauLevenshtein(ns[0], ns[1], 3));
      doit("JW ", new JaroWinklerSimilarity(ns[0], ns[1]));
      doit("MJW", new ModifiedJaroWinklerSimilarity(ns[0], ns[1]));
    }
  }

  public static void main (String[] args) {
    int[] sim = new int[]{60,70,80,85,90,91,92,93,94,95,96,97,98,99,100,101,102,105,110,115,120,130,140,150,160,175};
    for (int s : sim){
      int ns = s <= 90 ? s*10 : 900 + (int) (100d * (Math.log10((s-80d)*1.1) - 1) );

      System.out.println(s + " => " + (Math.log10((s-80d)*1.1)) );
      System.out.println(s + " => " + ns );
    }
  }

  private double doit(String name, StringSimilarity sim) {
    double s = sim.getSimilarity();
    LOG.debug(" {}={}", name, s);
    return s;
  }

  private double doitTime(String name, StringSimilarity sim) {
    long start = System.currentTimeMillis();
    double s = sim.getSimilarity();
    int repeat = 1000;
    while (repeat > 0) {
      sim.getSimilarity();
      repeat--;
    }
    long stop = System.currentTimeMillis();
    LOG.debug(" {}={}   1000x in {} ms", name, s, stop-start);
    return s;
  }


}
