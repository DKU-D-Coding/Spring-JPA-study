package hello.hellospring.domain;


import javax.persistence.*;

@Entity
public class Product{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String pname;
    private Long price;
    private Long heart;
    private String seller;

    public Long getpid() {
        return pid;
    }
    public void setpid(Long pid) { this.pid = pid; }
    public String getpname() {
        return pname;
    }
    public void setpname(String pname) {
        this.pname = pname;
    }
    public String getSeller() {return seller;}
    public void setSeller(String seller) { this.seller = seller;}
    public Long getPrice() { return price; }
    public void setPrice(Long price) { this.price = price; }
    public Long getHeart() { return heart; }
    public void setHeart(Long heart) { this.heart = heart; }
}