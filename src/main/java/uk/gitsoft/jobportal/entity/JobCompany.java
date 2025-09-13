package uk.gitsoft.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_company")
public class JobCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer companyId;

    private String name;
    private String logo;

    public JobCompany() {
    }

    public JobCompany(Integer companyId, String name, String logo) {
        this.companyId = companyId;
        this.name = name;
        this.logo = logo;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "JobCompany{" +
                "Id=" + companyId +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}