////package com.labconnect.services;
////import com.labconnect.DTOResponse.TestResponse;
////import com.labconnect.Enum.Status;
////import com.labconnect.DTORequest.TestRequest;
////import com.labconnect.Exception.TestException;
////import com.labconnect.Exception.TestResultNotFoundException;
////import com.labconnect.mapper.TestMapper;
////import com.labconnect.models.Test;
////import com.labconnect.repository.TestRepository;
////import org.springframework.stereotype.Service;
////import org.springframework.transaction.annotation.Transactional;
////import java.util.List;
////
////@Service
////public class TestService {
////        private final TestRepository testRepository;
////        private final TestMapper testMapper;
////
////        public TestService(TestRepository testRepository, TestMapper testMapper) {
////            this.testRepository = testRepository;
////            this.testMapper = testMapper;
////        }
////
////        @Transactional
////        public TestResponse createTest(TestRequest request) {
////            Test test = testMapper.toEntity(request);
////
////            if (test.getStatus() == null) {
////                test.setStatus(Status.Active);
////            }
////
////            Test saved = testRepository.save(test);
////            return testMapper.toResponseDto(saved);
////        }
////
////        @Transactional
////        public TestResponse updateTest(Long id, TestRequest request) {
////            Test existing = testRepository.findById(id)
////                    .orElseThrow(() -> new TestResultNotFoundException("Test not found"));
////            testMapper.updateEntity(request, existing);
////            Test saved = testRepository.save(existing);
////            return testMapper.toResponseDto(saved);
////        }
////
////        @Transactional(readOnly = true)
////        public TestResponse getTestById(Long id) {
////            Test test = testRepository.findById(id)
////                    .orElseThrow(() -> new TestException("Test not found"));
////            return testMapper.toResponseDto(test);
////        }
////
////        @Transactional
////        public void deactivateTest(Long id) {
////            Test test = testRepository.findById(id)
////                    .orElseThrow(() -> new TestException("Test not found"));
////            test.setStatus(Status.Inactive);
////            testRepository.save(test);
////        }
////
////        @Transactional(readOnly = true)
////        public List<TestResponse> getActiveTests() {
////            return testRepository.findByStatus(Status.Active)
////                    .stream()
////                    .map(testMapper::toResponseDto)
////                    .toList();
////        }
////    }
//
//
//package com.labconnect.services;
//
//import com.labconnect.DTOResponse.TestResponse;
//import com.labconnect.Enum.Status;
//import com.labconnect.DTORequest.TestRequest;
//import com.labconnect.Exception.TestException;
//import com.labconnect.Exception.TestResultNotFoundException;
//import com.labconnect.mapper.TestMapper;
//import com.labconnect.models.Test;
//import com.labconnect.repository.TestRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class TestService {
//
//    private final TestRepository testRepository;
//    private final TestMapper testMapper;
//
//    public TestService(TestRepository testRepository, TestMapper testMapper) {
//        this.testRepository = testRepository;
//        this.testMapper = testMapper;
//    }
//
//    @Transactional
//    public TestResponse createTest(TestRequest request) {
//        Test test = testMapper.toEntity(request);
//
//        // default to ACTIVE if not provided
//        if (test.getStatus() == null) {
//            test.setStatus(Status.Active);
//        }
//
//        Test saved = testRepository.save(test);
//        return testMapper.toResponseDto(saved);
//    }
//
//    @Transactional
//    public TestResponse updateTest(Long id, TestRequest request) {
//        Test existing = testRepository.findById(id)
//                .orElseThrow(() -> new TestResultNotFoundException("Test not found"));
//        testMapper.updateEntity(request, existing);
//        Test saved = testRepository.save(existing);
//        return testMapper.toResponseDto(saved);
//    }
//
//    @Transactional(readOnly = true)
//    public TestResponse getTestById(Long id) {
//        Test test = testRepository.findById(id)
//                .orElseThrow(() -> new TestException("Test not found"));
//        return testMapper.toResponseDto(test);
//    }
//
//    @Transactional
//    public void deactivateTest(Long id) {
//        Test test = testRepository.findById(id)
//                .orElseThrow(() -> new TestException("Test not found"));
//        test.setStatus(Status.Inactive);
//        testRepository.save(test);
//    }
//
//    @Transactional(readOnly = true)
//    public List<TestResponse> getActiveTests() {
//        return testRepository.findByStatus(Status.Active)
//                .stream()
//                .map(testMapper::toResponseDto)
//                .toList();
//    }
//}