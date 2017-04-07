package ba.unsa.etf.rma.elvircrn.rma17_17455

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Musician(var name: String, var surname: String = "", var genre: String  = "", var web: String = "", var bio: String = "") : Serializable, Parcelable {
    constructor(name: String) : this (name, "", "", "", "")

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Musician> = object : Parcelable.Creator<Musician> {
            override fun createFromParcel(source: Parcel): Musician = Musician(source)
            override fun newArray(size: Int): Array<Musician?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(surname)
        dest?.writeString(genre)
        dest?.writeString(web)
        dest?.writeString(bio)
    }
}