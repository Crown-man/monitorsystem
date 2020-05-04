package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Diagnosis;
import com.cidp.monitorsystem.model.ExportDiagnosis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiagnosisMapper {
    void insert(@Param("list") List<Diagnosis> list);

    void insertByDia(Diagnosis d);

    List<Diagnosis> getAllInfo();

    Integer updateRemark(@Param("desc") String desc, @Param("id") String id);

    String getRemark(@Param("id") String id);

    Integer updateIgnore(@Param("id") String id);

    Integer deleteById(@Param("id") String id);

    List<ExportDiagnosis> selectUnhandledDiagnosisInfo();

    List<ExportDiagnosis> selectProcessedDiagnosisInfo();

    List<ExportDiagnosis> selectIgnoreDiagnosisInfo();

    List<Diagnosis> selectDiagnosisByIPAndzhtype(@Param("ip") String ip,@Param("zhtype") String zhtype);
}
