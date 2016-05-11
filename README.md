# MultiTrackVOD

MultiTrackVOD is a module for [Wowza Streaming Engine™ media server software](https://www.wowza.com/products/streaming-engine) that can be used to configure which tracks are sent to the player.  

## Prerequisites

Wowza Streaming Engine 4.0.0 or later is required.

## Usage

This module provides the following functionality:

* The module uses query parameters that are added to the stream URLs to tell the server which tracks to select.
* You can specify audioindex, videoindex, and dataindex to identify an index for each track type.

## API Reference

[Wowza Streaming Engine Server-Side API Reference](https://www.wowza.com/resources/WowzaStreamingEngine_ServerSideAPI.pdf)

[How to extend Wowza Streaming Engine using the Wowza IDE](https://www.wowza.com/forums/content.php?759-How-to-extend-Wowza-Streaming-Engine-using-the-Wowza-IDE)

Wowza Media Systems™ provides developers with a platform to create streaming applications and solutions. See [Wowza Developer Tools](https://www.wowza.com/resources/developers) to learn more about our APIs and SDK.

To use the compiled version of this module, see [How to select multiple tracks from a VOD file (MultiTrackVOD)](https://www.wowza.com/forums/content.php?615-How-to-select-multiple-tracks-from-a-VOD-file-(ModuleMultiTrackVOD)). 

## Contact

[Wowza Media Systems, LLC](https://www.wowza.com/contact)

## License

This code is distributed under the [Wowza Public License](https://github.com/WowzaMediaSystems/wse-plugin-multitrackvod/blob/master/LICENSE.txt).
