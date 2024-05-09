package com.vupt.SHM.databuild;

import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.constant.Authority;
import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.entity.*;
import com.vupt.SHM.repositories.*;
import com.vupt.SHM.views.DepartmentController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Configuration
public class DataInitializer {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RepairterRepository repairterRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;


    private Role roleAdmin;
    private Role roleUser;

    private Account accountRoot;
    private Account accountUserA;
    private Account accountUserB;

    private Category categoryA;
    private Category categoryB;
    private Category categoryC;
    private Category categoryD;
    private Category categoryE;

    private Department departmentA;
    private Department departmentB;
    private Department departmentC;

    private Employee employeeA;
    private Employee employeeB;

    private Repairter repairterA;
    private Repairter repairterB;

    private Equipment equipmentA;
    @Bean
    public CommandLineRunner getCommandLineRunner() {
        return args -> {
            if(roleRepository.findAll().isEmpty()){
                roleAdmin=new Role(Authority.ROLE_ADMIN);
                roleUser=new Role(Authority.ROLE_USER);
                roleRepository.save(roleAdmin);
                roleRepository.save(roleUser);
            }
            if(accountRepository.findAll().isEmpty()){
                accountRoot=new Account("root",passwordEncoder.encode("123"),"root");
                accountUserA=new Account("vupt",passwordEncoder.encode("123"),"Phạm Tuấn Vũ");
                accountUserB=new Account("thuannv",passwordEncoder.encode("123"),"Nguyễn Văn Thuần");
                accountRepository.save(accountRoot);
                accountRepository.save(accountUserA);
                accountRepository.save(accountUserB);
            }
            if(categoryRepository.findAll().isEmpty()){

                categoryA=new Category("Máy in","MI");
                categoryB=new Category("Bộ CPU","CPU");
                categoryC=new Category("Màn hình","MH");
                categoryD=new Category("Bình mực","BM");
                categoryE=new Category("Bàn phím","BP");

                categoryRepository.save(categoryA);
                categoryRepository.save(categoryB);
                categoryRepository.save(categoryC);
                categoryRepository.save(categoryD);
                categoryRepository.save(categoryE);
              //  categoryRepository.save(categoryA);

            }
            if (departmentRepository.findAll().isEmpty()) {
                departmentA = new Department("Tổ CNTT", DepartmentType.TO);
                departmentB=new Department("Phòng Cấp Cứu",DepartmentType.PHONG);
                departmentC=new Department("Khoa Nội Tổng Hợp",DepartmentType.KHOA);

                departmentRepository.save(departmentA);
                departmentRepository.save(departmentB);
                departmentRepository.save(departmentC);
            }
            if (employeeRepository.findAll().isEmpty()){
                employeeA = new Employee("Phạm Tuấn Vũ");
                employeeA.setDepartment(departmentA);
                employeeRepository.save(employeeA);

                employeeB = new Employee("Nguyễn Văn Thuần");
                employeeB.setDepartment(departmentA);
                employeeRepository.save(employeeB);
            }

            if(repairterRepository.findAll().isEmpty()){
                repairterA = new Repairter("Anh Sang","Computer Sang","0913xxx");
                repairterB=new Repairter("Anh Văn","Computer Văn","0912xxx");
                repairterRepository.save(repairterA);
                repairterRepository.save(repairterB);
            }

            if(equipmentRepository.findAll().isEmpty()){
                equipmentA=new Equipment();
                equipmentA.setName("Máy in Cannon 2900");
                equipmentA.setCode("MI1");
                equipmentA.setReceivedDate(Date.valueOf(LocalDate.of(2020,01,10)));
                Category category=categoryRepository.findById(01L).get();
                equipmentA.setCategory(category);
                Department department=departmentRepository.findById(01L).get();
                equipmentA.setDepartment(department);
                Employee employee=employeeRepository.findById(01L).get();
                equipmentA.setManager(employee);
                equipmentA.setStatus(EquipmentStatus.USED);
                equipmentRepository.save(equipmentA);
            }
        };

    }

    public void initAccount() {
        System.out.println("runner");
        Role roleAdmin = new Role(Authority.ROLE_ADMIN);
        //roleRepository.save(roleAdmin);
        Account root = new Account("root", passwordEncoder.encode("123"), "root");
        root.getRoles().add(roleAdmin);

        accountRepository.save(root);


    }

    public void initCategory() {
        //Category
        categoryRepository.save(new Category("Máy in", "PRT"));
        //  accountRepository.save(new Account("vupt","123","Pham Tuan Vu"));
    }

    public void initDepartment() {
        departmentRepository.save(new Department("Tổ CNTT", DepartmentType.TO));
        departmentRepository.save(new Department("Phòng Cấp Cứu", DepartmentType.PHONG));
        departmentRepository.save(new Department("Khoa Sản", DepartmentType.KHOA));
    }

    public void initEmployee() {


    }
}
