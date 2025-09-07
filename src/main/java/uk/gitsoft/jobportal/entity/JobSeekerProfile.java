package uk.gitsoft.jobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private int userAccountId;
    @OneToOne
    @JoinColumn(name = "user_accouunt_id")
    @MapsId
    private Users userId;
    private String firstName;
    private String lastName;
    private String city;
    private String workAuthorizations;
    private String employmentType;
    private String resume;
    @Column(nullable = true, length = 64)
    private String profilePhoto;
    @OneToMany(targetEntity = Skills.class, mappedBy = "jobSeekerProfile", cascade = CascadeType.ALL)
    private List<Skills> skills;

    public JobSeekerProfile() {
    }

    public JobSeekerProfile(Users userId) {
        this.userId = userId;
    }

    public JobSeekerProfile(int userAccountId, String firstName, String lastName, String city, String workAuthorizations, String employmentType, String resume, String profilePhoto) {
        this.userAccountId = userAccountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.workAuthorizations = workAuthorizations;
        this.employmentType = employmentType;
        this.resume = resume;
        this.profilePhoto = profilePhoto;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWorkAuthorizations() {
        return workAuthorizations;
    }

    public void setWorkAuthorizations(String workAuthorizations) {
        this.workAuthorizations = workAuthorizations;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", workAuthorizations='" + workAuthorizations + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", resume='" + resume + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", skills=" + skills +
                '}';
    }
}
