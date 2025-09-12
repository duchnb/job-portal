package uk.gitsoft.jobportal.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobPostId;

    @ManyToOne
    @JoinColumn(name = "postedById", referencedColumnName = "user_id")
    private Users postedById;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobLocationId", referencedColumnName = "Id")
    private JobLocation jobLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobCompanyId", referencedColumnName = "Id")
    private JobCompany jobCompanyId;

    @Transient
    private Boolean isActive;

    @Transient
    private Boolean isSaved;

    @Length(max = 1000)
    private String descriptionOfJob;

    private String jobType;
    private String salary;
    private String remote;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date PostedDate;
    private String jobTitle;

    public JobPostActivity() {
    }

    public JobPostActivity(Integer jobPostId, Users postedById, JobLocation jobLocation, JobCompany jobCompanyId, Boolean isActive, Boolean isSaved, String descriptionOfJob, String jobType, String salary, String remote, Date postedDate, String jobTitle) {
        this.jobPostId = jobPostId;
        this.postedById = postedById;
        this.jobLocation = jobLocation;
        this.jobCompanyId = jobCompanyId;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.descriptionOfJob = descriptionOfJob;
        this.jobType = jobType;
        this.salary = salary;
        this.remote = remote;
        PostedDate = postedDate;
        this.jobTitle = jobTitle;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }

    public Users getPostedById() {
        return postedById;
    }

    public void setPostedById(Users postedById) {
        this.postedById = postedById;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    // Alias accessors for Thymeleaf form binding compatibility
    // The template uses "jobLocationId" as the property name, but the entity field is "jobLocation".
    // Providing these accessors exposes a bean property named "jobLocationId" that maps to the same field.
    @Transient
    public JobLocation getJobLocationId() {
        return jobLocation;
    }

    public void setJobLocationId(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public JobCompany getJobCompanyId() {
        return jobCompanyId;
    }

    public void setJobCompanyId(JobCompany jobCompanyId) {
        this.jobCompanyId = jobCompanyId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public String getDescriptionOfJob() {
        return descriptionOfJob;
    }

    public void setDescriptionOfJob(String descriptionOfJob) {
        this.descriptionOfJob = descriptionOfJob;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public Date getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(Date postedDate) {
        PostedDate = postedDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "JobPostActivity{" +
                "jobPostId=" + jobPostId +
                ", postedById=" + postedById +
                ", jobLocation=" + jobLocation +
                ", jobCompanyId=" + jobCompanyId +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", descriptionOfJob='" + descriptionOfJob + '\'' +
                ", jobType='" + jobType + '\'' +
                ", salary='" + salary + '\'' +
                ", remote='" + remote + '\'' +
                ", PostedDate=" + PostedDate +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
