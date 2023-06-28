package g54516.hirehub.database.dto

import android.os.Parcel
import android.os.Parcelable

data class DeveloperDto(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val domain: String = "",
    val experience_level: String = "",
    val gender: String = "",
    val phoneNumber: Int = 0,
    val rating: Int = 0,
    val avatar: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(domain)
        parcel.writeString(experience_level)
        parcel.writeInt(rating)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeveloperDto> {
        override fun createFromParcel(parcel: Parcel): DeveloperDto {
            return DeveloperDto(parcel)
        }

        override fun newArray(size: Int): Array<DeveloperDto?> {
            return arrayOfNulls(size)
        }
    }
}
