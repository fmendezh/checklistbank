package org.gbif.checklistbank.service.mybatis;

import org.gbif.api.model.checklistbank.VernacularName;

import org.apache.ibatis.annotations.Param;

/**
 * The MyBatis mapper interface for VernacularName.
 */
public interface VernacularNameMapper extends NameUsageComponentMapper<VernacularName> {

  /**
   * Retrieves a single vernacular name or none for a given checklist usage and language.
   */
  VernacularName getByChecklistUsage(@Param("key") int usageKey, @Param("lang") String language);

  /**
   * Retrieves a single vernacular name or none for a given nub usage and language.
   */
  VernacularName getByNubUsage(@Param("key") int usageKey, @Param("lang") String language);
}
