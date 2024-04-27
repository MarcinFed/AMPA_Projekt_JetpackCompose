package com.example.projektmarcinfedorowicz.database

import java.io.Serializable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item_table")
class DBItem : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id =0
    var Product : String = ""
    var Use : String = ""
    var Amount : Int = 0
    var Bought : Boolean = false
    var ProductType : Int = 0
    var Available : Boolean = false

    constructor()
    constructor(Product: String, Use: String, Amount: Int, ProductType: Int, Available: Boolean) {
        this.Product = Product
        this.Use = Use
        this.Amount = Amount
        this.ProductType = ProductType
        this.Available = Available
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}