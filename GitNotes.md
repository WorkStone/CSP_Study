###git push failed
    一直push失败，连接github仓库是成功了的。使用的是ssh方式，但是Permission denied (publickey)。
    后面在.git文件夹里的config文件中设置了url，使用http协议传输成功了。
    另外，如果http协议失败，可以:
    git config --global --unset http.proxy
    git config --global --unset http.proxy
    
###一个使用git管理项目的帖子
    https://blog.csdn.net/luxinfeng666/article/details/106221002