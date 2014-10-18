Carbon
======

Carbon is a plugin which adds 1.8 blocks and features into a spigot protocol hacked server.

<h4><b>Proper issue format is: [issue type] Title of Issue</b></h4>
<h4><b>Proper pull request format is: [PR type] Title of Pull Request</b></h4>
If your issue/pull request is properly created, a label will be given to your issue/pull request.

More stable releases are available [on Spigot](http://www.spigotmc.org/resources/.1258/).  
If you find a bug, post an issue containing the version you are using!  
The latest builds are available from [Jenkins](http://build.true-games.org/job/Carbon/).
Jenkins builds are not guaranteed to be stable.  
To prevent 1.7 clients from crashing, please install [ProtocolLib](http://assets.comphenix.net/job/ProtocolLib%20-%20Spigot%20Compatible%201.8/).

.

Some things to note before downloading and compiling the repository:

- Make sure you always use the latest version and you have all of the dependencies installed locally.
- If any errors occur while using this plugin in game, be sure to post an issue with your spigot version, Carbon version, and any steps to reproduce the issue.

How to compile the repository:
Compiling the project is easy as 1, 2, and 3.
1) Fork the project or download the sources.
2) Open the gradle project in your favorite IDE. You may see errors when you first open it.
3) Compile the projects and the errors should magically disappear.
Extra: You may have to reload the project after building.

Some things to note before submitting a pull request:

- Make sure you always use Carbon.injector() for retrieving references to new blocks, materials, and items
- Make sure all the code you submit is compliant with Java 6
- Make sure all dependencies added have an appropriate Maven repository in the pom.xml.
- Only submit the source code, pom.xml, and plugin-related resources to the repository.
- Do not submit a pull-request for very minor changes. Ex: Spelling, spacing, etc. You may submit a pull request that changes many files at once for spelling and spacing.
- Please use all proper Java conventions Ex: No capitalized fields unless constant, no capitalized methods, etc...
