package com.labconnect.mapper;
import com.labconnect.DTORequest.TestRequest;
import com.labconnect.DTOResponse.TestResponse;
import com.labconnect.Enum.Department;
import com.labconnect.Enum.Method;
import com.labconnect.Enum.Status;
import com.labconnect.models.Test;
import com.labconnect.Enum.EnumUtil;
import org.springframework.stereotype.Component;

@Component
public class TestMapper {

    public Test toEntity(TestRequest req) {
        Test t = new Test();
        if (req.getName() != null) t.setName(req.getName());

        if (req.getDepartment() != null) {
            t.setDepartment(EnumUtil.fromStringIgnoreCase(Department.class, req.getDepartment()));
        }
        if (req.getMethod() != null) {
            t.setMethod(EnumUtil.fromStringIgnoreCase(Method.class, req.getMethod()));
        }
        if (req.getStatus() != null) {
            t.setStatus(EnumUtil.fromStringIgnoreCase(Status.class, req.getStatus()));
        }
        return t;
    }

    public void updateEntity(TestRequest req, Test entity) {
        if (req.getName() != null) entity.setName(req.getName());

        if (req.getDepartment() != null) {
            entity.setDepartment(EnumUtil.fromStringIgnoreCase(Department.class, req.getDepartment()));
        }
        if (req.getMethod() != null) {
            entity.setMethod(EnumUtil.fromStringIgnoreCase(Method.class, req.getMethod()));
        }
        if (req.getStatus() != null) {
            entity.setStatus(EnumUtil.fromStringIgnoreCase(Status.class, req.getStatus()));
        }
    }

    public TestResponse toResponseDto(Test entity) {
        return TestResponse.builder()
                .testId(entity.getTestId())
                .name(entity.getName())
                .department(entity.getDepartment() == null ? null : entity.getDepartment().name())
                .method(entity.getMethod() == null ? null : entity.getMethod().name())
                .status(entity.getStatus() == null ? null : entity.getStatus().name())
                .build();
    }
}
