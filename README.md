PluginRequest-ExtraHealth
=========================

Adds extra health with permissions

Configuration
=============
You can configure ExtraHealth in the file plugins/ExtraHealth/permissions.yml

The permissions.yml file has one option, which is really a list of options.

You can add your own permissions into the file, with the format of 
'permissions: value'. Then after a restart, if a player has the permission
'permission', they will get extra health equal to 'value'. If a player has
multiple permissions, the one with the highest boost is chosen. Possible values
can be integers (like '2') or doubles (like '2.5').