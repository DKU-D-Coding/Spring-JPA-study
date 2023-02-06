
package hello.hellospring.domain;
import javax.persistence.*;

@Entity
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String cname;
    private String email;
    private String password;
    private String phone;
    private String nickname;


    public Long getcid() { return cid; }
    public void setcid(Long cid) { this.cid = cid; }
    public String getcname() {
        return cname;
    }
    public void setcname(String cname) {
        this.cname = cname;
    }
    public String getemail() {return email;}
    public void setemail(String email) { this.email = email;}
    public String getpassword() { return password;}
    public void setpassword(String password) { this.password = password;}
    public String getphone() { return phone;}
    public void setphone(String phone) {this.phone = phone;}
    public String getnickname() {return nickname;}
    public void setnickname(String nickname) {this.nickname = nickname;}
}
