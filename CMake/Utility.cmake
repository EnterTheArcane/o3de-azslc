#
# Copyright (c) Contributors to the Open 3D Engine Project.
# For complete copyright and license terms please see the LICENSE at the root of this distribution.
#
# SPDX-License-Identifier: Apache-2.0 OR MIT
#
#

include_guard(GLOBAL)

# Define a cache variable that can also be set via an environment variable of
# the same name.  The environment variable is only read when the cache entry
# does not already exist (i.e. first configure or after deleting the cache).
macro(azslc_option name default type description)
    if(NOT DEFINED ${name} AND DEFINED ENV{${name}})
        set(${name} "$ENV{${name}}")
    endif()
    set(${name} "${default}" CACHE ${type} "${description}")
endmacro()
