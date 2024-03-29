package org.gbif.checklistbank.service.mybatis;

import org.gbif.api.model.checklistbank.NameUsage;
import org.gbif.api.model.common.paging.Pageable;
import org.gbif.api.model.common.paging.PagingResponse;
import org.gbif.checklistbank.service.UsageService;
import org.gbif.checklistbank.service.mybatis.model.Usage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;

import com.google.inject.Inject;
import com.jolbox.bonecp.ConnectionHandle;
import org.postgresql.PGConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the NameUsageService using MyBatis.
 * All PagingResponses will not have the count set as it can be too costly sometimes.
 */
public class UsageServiceMyBatis implements UsageService {

  private static final Logger LOG = LoggerFactory.getLogger(UsageServiceMyBatis.class);

  private final NameUsageMapper mapper;
  private final UsageMapper usageMapper;

  @Inject
  private DataSource ds;

  @Inject
  UsageServiceMyBatis(NameUsageMapper mapper, UsageMapper usageMapper) {
    this.mapper = mapper;
    this.usageMapper = usageMapper;
  }

  @Override
  public List<Integer> listAll() {
    Connection con = null;
    try {
      con = ds.getConnection();
      ConnectionHandle boneCon = (ConnectionHandle) con;
      PGConnection pgcon = (PGConnection) boneCon.getInternalConnection();
      IntArrayPgWriter intMapper = new IntArrayPgWriter();
      pgcon.getCopyAPI().copyOut("copy (select id from name_usage) TO STDOUT WITH NULL '' ", intMapper);
      return intMapper.result();
    } catch (Exception e) {
      LOG.error("Failed to load all usage ids", e);
      throw new RuntimeException("Exception while loading usage ids", e);
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
        }
      }
    }
  }

  @Override
  public Integer maxUsageKey(UUID datasetKey) {
    return mapper.maxUsageKey(datasetKey);
  }

  @Override
  public List<NameUsage> listRange(int usageKeyStart, int usageKeyEnd) {
    return mapper.listRange(usageKeyStart, usageKeyEnd);
  }

  @Override
  public PagingResponse<Usage> list(UUID datasetKey, Pageable page) {
    return new PagingResponse<Usage>(page, null, usageMapper.list(datasetKey, page));
  }

}
