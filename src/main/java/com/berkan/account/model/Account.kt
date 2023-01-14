package com.berkan.account.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Account(

        @Id
        @GeneratedValue(generator = "UUID") //timestamp i alır ve bir hash üretir. 32 digit harf ve rakamdan oluşur.
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.idUUIDGenerator")
        val id: String?, // ? bu alan nullable olabilir demek. Birden fazla constructor yaratıyor bunların null olduğu olmadığı versiyonlarını
        val balance: BigDecimal? = BigDecimal.ZERO,
        val creationDate: LocalDateTime,
        //cascadetype all= ilişkiye ait entityde yapılacak crud işlemlerini customer a da uygula. persist= insert de merge=update işleminde delete=sil hepsini
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) //lazy = account verisini çektiğimde customer verisi gelmesin. account.getcustomer ı çağırana kadar içi boş. bu metodu çağırdığın zaman hibernate araya giriyor ve datayı çekip getiriyor.
        @JoinColumn(name = "customer_id", nullable = false)
        val customer: Customer?,

        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
        val transaction: Set<Transaction>?,
)

//ManyToOne kullandığımız için hashcode metodunu override etmemiz gerekiyor.