package aula.com.projeto.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NM_NAME")
    private String name;
    private String email;
    private String cpf;
    @Column(name = "QRCode", length = 3000)
    private String qrcode ;

    public User() {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.qrcode = qrcode;
    }

    public User(String teste, String s) {
    }

    public String getCpf() {return cpf;}

    public void setCpf(String cpf){this.cpf = cpf;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQrcode(){return qrcode;}

    public void setQrcode(String s){this.qrcode = qrcode;}
}
