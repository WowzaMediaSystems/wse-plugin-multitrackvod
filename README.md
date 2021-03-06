# MultiTrackVOD

The **MultiTrackVOD** module for [Wowza Streaming Engine™ media server software](https://www.wowza.com/products/streaming-engine) can be used to configure which tracks are sent to the player.

This repo includes a [compiled version](/lib/wse-plugin-multitrackvod.jar).

## Prerequisites

Wowza Streaming Engine 4.0.0 or later is required.

## Usage

This module provides the following functionality:

* Use query parameters added to the stream URLs to tell the server which tracks to select in a VOD file.
* Use **audioindex**, **videoindex**, and **dataindex** parameters to specify an index value for each track type.

## More resources
To use the compiled version of this module, see [Select multiple tracks from a VOD file with a Wowza Streaming Engine Java module](https://www.wowza.com/docs/how-to-select-multiple-tracks-from-a-vod-file-modulemultitrackvod). 

[Wowza Streaming Engine Server-Side API Reference](https://www.wowza.com/resources/serverapi/)

[How to extend Wowza Streaming Engine using the Wowza IDE](https://www.wowza.com/docs/how-to-extend-wowza-streaming-engine-using-the-wowza-ide)

Wowza Media Systems™ provides developers with a platform to create streaming applications and solutions. See [Wowza Developer Tools](https://www.wowza.com/developer) to learn more about our APIs and SDK.

## Contact

[Wowza Media Systems, LLC](https://www.wowza.com/contact)

## License

This code is distributed under the [Wowza Public License](/LICENSE.txt).
