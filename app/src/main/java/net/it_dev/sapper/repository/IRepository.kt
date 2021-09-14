package net.it_dev.sapper.repository

import net.it_dev.sapper.util.Resource
import java.io.File

interface IRepository {
	fun getFile(path:String):Resource<File>
}