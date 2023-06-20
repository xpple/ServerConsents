## Server Consent for Clients

_Perhaps a different name would be better._

Summary
-------

This plugin is a proof of concept for a universal server consent system
for mods and features of mods a client connecting to the server uses. The
server configures a list of illegal mods/features and, if enabled, will
send the client these lists when the client connects to the server. Mod
developers can then process this information and if needed disable
certain/all functionality. This system is meant to be standardised across
different server software implementations, so that clients know what to
implement.

Goals
-----

- Standardise a system for client mod developers wanting their mod to be
used responsibly. This is the main goal!
- Provide server owners with a system that can _ask_ the client to
disable some of its mods or some of its mods' functionalities.

Non-Goals
---------

It is absolutely not a goal to provide server owners with a robust
anti-cheat system. This system should not be used in place of anti-cheat
plugins and other unfair gameplay preventing plugins/mods. This is not
the intention of this system.

Success Metrics
---------------

The amount of client-sided mods that will adopt this system. Though, it
should be noted that this system will not have failed if some mods don't
use this; for servers this system does not have any downsides and any mod
using this is therefore a success.

Motivation
----------

Client mod developers are often powerless to prevent their mods from
being used on servers which do not allow them/do not allow certain of
their functionalities. For instance, many cheat mod developers wouldn't
want their mods to be used outside of anarchy servers, but they have no
way of preventing it. Others might completely dismiss certain features
in fear that they will be used irresponsibly. With this system, client
mod developers are no longer limited in this regard, as they can now
disable their mod/illegal features on servers that want them to.

The implementation as shown by the plugin is very minimal, and server
owners only need to change a config to enable this system. This is
beneficial for server owners as it barely requires any effort to
configure.

Description
-----------

This system uses the following two plugin channels (namespace should be
discussed).

- `noconsent:mods`
- `noconsent:features`

If the client registers these channels, and the server has enabled this
system, the server will send the configured list of mods/features in the
corresponding channel to the client. As this is a proof of concept, this
plugin uses the `config.yml` file for the configurations. The
implementation in any server software will of course be different. By
default, the configuration file is the following (YAML was used as
example for clarity).

```yml
enabled: false
illegalMods: []
illegalFeatures: []
```

When the client receives the list of illegal mods/features, an event will
be triggered by the mod loader/api to which client mod developers can
listen. They can then check if their mod is included in the illegal mods
list, or if a feature they provide is included in the illegal features
list. It is up to the mod developer to decide what to do with this
information.

Alternatives
------------

Alternatively, this system could not be standardised. In that case a
plugin (in the case of a Paper server) like this one could be used by
server owners. However, there is very little chance a third party plugin
will be widely adopted by servers. Importantly, standardisation is the
main goal of this system. Without it, disagreement and inconsistency
would arise, and the system would flop. Only if established server
software providers choose to adopt this system will this work.
