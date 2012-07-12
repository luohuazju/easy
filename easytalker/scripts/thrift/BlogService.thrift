namespace java com.sillycat.easytalker.plugins.thrift.gen.code   #  package name

struct Blog {   #  POJO
	1: string topic
	2: binary content
	3: i64    createdTime
	4: string id
	5: string ipAddress
	6: map<string,string> props
}


service BlogService {  #  interface class
    string createBlog(1:Blog blog)
    list<string> batchCreateBlog(1:list<Blog> blogs)
    string deleteBlog(1:string id)
    list<Blog> listAll()
    Blog getOne(1:string id)
    string updateBlog(1:Blog blog)
}


