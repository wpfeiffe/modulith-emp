package com.wspfeiffer.modulithemp.company.internal;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>
{
    Page<Department> findByDeptCode( String deptCode, Pageable p);
}
