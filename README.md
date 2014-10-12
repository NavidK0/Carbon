Carbon
======

Carbon is a plugin which adds 1.8 blocks and features into a spigot protocol hacked server.

NEW: WHEN SUBMITTING A PULL REQUEST, PLEASE USE PROPER LABELS!!!

More stable releases are available [on Spigot](http://www.spigotmc.org/resources/.1258/).  
If you find a bug, post an issue containing the version you are using!  
The latest builds are available from [Jenkins](http://ci.citizensnpcs.co/job/Carbon/). Download Carbon.jar, not Carbon-1.jar!  
Jenkins builds are not guaranteed to be stable.  
To prevent 1.7 clients from crashing, please install [ProtocolLib](http://assets.comphenix.net/job/ProtocolLib%20-%20Spigot%20Compatible%201.8/).

.

Some things to note before downloading and compiling the repository:

- Make sure you always use the latest version and you have all of the dependencies installed locally.
- If any errors occur while using this plugin in game, be sure to post an issue with your spigot version, Carbon version, and any steps to reproduce the issue.


Some things to note before submitting a pull request:

- Make sure you always use Carbon.injector() for retrieving references to new blocks, materials, and items
- Make sure all the code you submit is compliant with Java 6
- Make sure all dependencies added have an appropriate Maven repository in the pom.xml.
- Only submit the source code, pom.xml, and plugin-related resources to the repository.
- Do not submit a pull-request for very minor changes. Ex: Spelling, spacing, etc. You may submit a pull request that changes many files at once for spelling and spacing.
- Please use all proper Java conventions Ex: No capitalized fields unless constant, no capitalized methods, etc...
