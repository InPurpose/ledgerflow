package org.stephen.ledgerflow.identity.department;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stephen.ledgerflow.common.exception.DepartmentNotFoundException;
import org.stephen.ledgerflow.common.exception.DuplicateDepartmentCodeException;
import org.stephen.ledgerflow.identity.department.api.CreateDepartmentRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    public void createDepartment_whenCodeDoesNotExist_savesAndReturnsDepartment() {

        when(departmentRepository.existsByCode("LEG")).thenReturn(false);
        when(departmentRepository.save(any(Department.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        CreateDepartmentRequest request = new CreateDepartmentRequest("Legal","LEG");
        Department department = departmentService.createDepartment(request);

        assertThat(department.getName()).isEqualTo("Legal");
        assertThat(department.getCode()).isEqualTo("LEG");
        assertThat(department.getId()).isNotNull();
        assertThat(department.getCreatedAt()).isNotNull();

        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);

        verify(departmentRepository).existsByCode("LEG");
        verify(departmentRepository).save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        assertThat(savedDepartment.getName()).isEqualTo("Legal");
        assertThat(savedDepartment.getCode()).isEqualTo("LEG");
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getCreatedAt()).isNotNull();

    }

    @Test
    public void createDepartment_whenCodeAlreadyExists_throwsExceptionAndDoesNotSave() {

        when(departmentRepository.existsByCode("FIN")).thenReturn(true);

        CreateDepartmentRequest request = new CreateDepartmentRequest(
                "Another Finance Department",
                "FIN");

        assertThatThrownBy(()-> {
            departmentService.createDepartment(request);
        }).isInstanceOf(DuplicateDepartmentCodeException.class)
                .hasMessage("Department code 'FIN' already exists");

        verify(departmentRepository).existsByCode("FIN");
        verify(departmentRepository,never()).save(any(Department.class));
    }

    @Test
    public void getDepartment_whenDepartmentExists_returnsDepartment() {
        final UUID id = UUID.fromString("4fcb10fe-1728-4b5a-8798-8f487b676709");
        Department expectedDepartment = new Department(
                "Finance",
                "FIN"

        );
        expectedDepartment.setId(id);
        when(departmentRepository.findById(id)).thenReturn(Optional.of(expectedDepartment));

        Department department = departmentService.getDepartment(id);

        assertThat(department).isSameAs(expectedDepartment);

        verify(departmentRepository).findById(id);
    }

    @Test
    public void getDepartment_whenDepartmentDoesNotExist_throwsException() {
        final UUID id = UUID.fromString("4fcb10fe-1234-5678-9101-8f487b676709");

        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> {
            departmentService.getDepartment(id);
        }).isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("Department '" + id +"' was not found");

        verify(departmentRepository).findById(id);

    }


}
