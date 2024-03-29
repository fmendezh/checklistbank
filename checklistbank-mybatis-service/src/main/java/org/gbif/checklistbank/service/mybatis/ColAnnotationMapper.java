package org.gbif.checklistbank.service.mybatis;

import org.gbif.checklistbank.service.mybatis.model.ColAnnotation;

import org.apache.ibatis.annotations.Param;

public interface ColAnnotationMapper {

  void delete(@Param("key") int taxonKey);

  void create(@Param("anno") ColAnnotation annotation);

}
