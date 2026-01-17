package com.hybris.plaincontainers

class EntryBase {
    var name: String? = null
    var thumbnailSrc: String? = null

    constructor(name: String, thumbnailSrc: String) {
        this.name = name
        this.thumbnailSrc = thumbnailSrc
    }
}