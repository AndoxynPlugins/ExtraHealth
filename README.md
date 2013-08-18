ExtraHealth
===========

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

Example permissions.yml
=======================
```
permissions:
  extrahealth.basic: 3
  extrahealth.vip: 10
  extrahealth.admin: 30
  overlord.health: 200
```
With this config:
* People with the permission 'extrahealth.basic' will get 3 more hearts.
* People with the permission 'extrahealth.vip' will get 10 more hearts.
* People with the permission 'extrahealth.master' will get 30 more hearts.
* People with the permission 'overlord.health' will get 200 more hearts.
The great thing about ExtraHealth is that you can have as many of these permissions as you want, allowing for the ultimate customizability.

World support
=============
If your permissions plugin supports per-world permissions, then ExtraHealth will work with that.
ExtraHealth re-checks for the permissions every time a player changes their world.

If your permission plugin doesn't support per-world permissions, I would recommend switching to zPermissions.