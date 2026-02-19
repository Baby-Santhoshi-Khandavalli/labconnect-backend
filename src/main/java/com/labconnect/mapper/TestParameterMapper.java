package com.labconnect.mapper;
import com.labconnect.DTORequest.TestParameterRequest;
import com.labconnect.DTOResponse.TestParameterResponse;
import com.labconnect.Enum.Flag;
import com.labconnect.models.Test;
import com.labconnect.models.TestParameter;
import com.labconnect.Enum.EnumUtil;   // <-- add this import
import org.springframework.stereotype.Component;

@Component
public class TestParameterMapper {

    public TestParameter toEntity(TestParameterRequest req, Test test) {
        TestParameter tp = new TestParameter();
        if (req.getName() != null) tp.setName(req.getName());
        if (req.getUnit() != null) tp.setUnit(req.getUnit());
        if (req.getReferenceRange() != null) tp.setReferenceRange(req.getReferenceRange());
        if (req.getCriticalRange() != null) {
            tp.setCriticalRange(EnumUtil.fromStringIgnoreCase(Flag.class, req.getCriticalRange()));
        }
        tp.setTest(test);
        return tp;
    }

    public void updateEntity(TestParameterRequest req, TestParameter entity) {
        if (req.getName() != null) entity.setName(req.getName());
        if (req.getUnit() != null) entity.setUnit(req.getUnit());
        if (req.getReferenceRange() != null) entity.setReferenceRange(req.getReferenceRange());
        if (req.getCriticalRange() != null) {
            entity.setCriticalRange(EnumUtil.fromStringIgnoreCase(Flag.class, req.getCriticalRange()));
        }
    }

    public TestParameterResponse toResponse(TestParameter entity) {
        TestParameterResponse resp = new TestParameterResponse();
        resp.setParameterId(entity.getParameterId());
        resp.setName(entity.getName());
        resp.setUnit(entity.getUnit());
        resp.setReferenceRange(entity.getReferenceRange());
        resp.setCriticalRange(entity.getCriticalRange() == null ? null : entity.getCriticalRange().name());
        resp.setTestId(entity.getTest() == null ? null : entity.getTest().getTestId());
        return resp;
    }
}
