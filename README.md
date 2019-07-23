README
===========================
```Java
//add tools plugin
apply plugin: 'com.tools.plugin'

//extension
tools {
    doubleClickExtension {
        need true
        filter {
            packageNames "com.tools.demo"
            classNames "MainActivity"
        }
    }
}