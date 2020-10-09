Git的分支必须指向一个commit，没有任何的commit就没有任何分支。

提交第一个commit之后git会自动创建一个分支。



对当前git 进行重新命名

git branch -m "branch_name"



git本地有几个

git status比较的是工作区



git remote 

列出已经存在的远程分支



Git branch 

列出本地的所有分支

git branch -a

列出本地和远程的所有分支

git branch -r

列出远程的所有分支



将本地分支推送到远程



Gits删除本地分支和同步远程已经删除的分支

删除本地分支

git branch -d [branch_name]

同步远程分支

git remote prune [origin]



Git amend相关

Git amend相关
当某一次提交后，需要一些小的修改，但又不想再做新的提交时，可以使用git amendl来追加提交。