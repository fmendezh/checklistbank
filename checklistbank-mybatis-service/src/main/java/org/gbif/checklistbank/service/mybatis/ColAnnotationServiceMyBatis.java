package org.gbif.checklistbank.service.mybatis;


import org.gbif.checklistbank.service.ColAnnotationService;
import org.gbif.checklistbank.service.mybatis.model.ColAnnotation;

import com.google.inject.Inject;

public class ColAnnotationServiceMyBatis implements ColAnnotationService {
  private ColAnnotationMapper mapper;

  @Inject
  ColAnnotationServiceMyBatis(ColAnnotationMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void insertAnnotation(ColAnnotation annotation) {
    mapper.delete(annotation.getTaxonId());
    mapper.create(annotation);
  }
}
