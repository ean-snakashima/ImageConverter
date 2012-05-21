
Image Converter 
===============

Requires
--------

### 1. ImageMagick & Jmagick ###
See : http://www.jmagick.org

***for windows***

    Image Magick
        ImageMagick-6.3.9-0-Q16-windows-dll.exe

    JMagick
        jmagick-win-6.3.9-Q16.zip
			
***for linux***
*Image Magick*
http://sourceforge.net/projects/imagemagick/files/old-sources/6.x/6.2/ImageMagick-6.2.9-8.tar.gz/download

    ./configure
	make all
    make install
    
    add "export LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH" into ".bash_profile"
    add "/usr/local/lib" into "/etc/ld.so.conf"
    ldconfig
    
*JMagick*
http://www.imagemagick.org/download/java/JMagick-6.2.6-0.tar.gz
The error would occur in compiling "test" source code. It's requred to delete "src/test".
    
    export JAVA_HOME=/usr/java/default
    export CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/jre/lib
    export JAVA_BIN=$JAVA_HOME/bin
    export JRE_HOME=$JAVA_HOME/jre
    export PATH=$PATH:$JAVA_BIN
    export JAVA_HOME CLASSPATH PATH
    
    ./configure
    make all
    make install
    
    mv lib/jmagick.jar /usr/local/lib
    mv lib/jmagick.jar $JAVA_HOME/jre/lib/ext
    mv lib/libJMagick.so /usr/local/lib
    mv lib/libJMagick.so $JAVA_HOME/jre/lib/amd64/

### 2. Tomcat ####

Parameters
----------

1.`height` : 
height of resized image
2.`width` : 
width of resized image
3.`path` : 
image path (Http or file)
4.`quality` : 
0 - 100

>e.g.
>>`http://localhost:8080/img?height=600&width=600&path=http%3A%2F%2Fimages.travelnow.com%2Fhotels%2F2000000%2F1700000%2F1699500%2F1699407%2F1699407_61_b.jpg&quality=10`


Note
----
###Recommend you to use mod_cache and mod_proxy.###

###Example settings.###

    LoadModule cache_module modules/mod_cache.so
    LoadModule disk_cache_module modules/mod_disk_cache.so
    
    ProxyPass /img ajp://127.0.0.1:8009/imgconv smax=0 ttl=60 retry=5
    ProxyPassReverse /img ajp://127.0.0.1:8009/imgconv
    
    <IfModule mod_cache.c>
            Header set Expires "Thu, 01 Jan 2099 00:00:00 GMT"
            CacheIgnoreCacheControl On
            CacheIgnoreNoLastMod On
            CacheMaxExpire 604800
            <IfModule mod_disk_cache.c>
                    CacheRoot /var/tmp/cache
                    CacheEnable disk /img
                    CacheDirLevels 6
                    CacheDirLength 3
            </IfModule>
    </IfModule>
