/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.graphhopper.routing.util.parsers;

import com.graphhopper.TimeDependentAccessRestriction;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.profiles.EncodedValue;
import com.graphhopper.routing.profiles.EncodedValueLookup;
import com.graphhopper.routing.profiles.SimpleBooleanEncodedValue;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.IntsRef;

import java.util.List;

public class OSMConditionalRestrictionParser implements TagParser {

    private SimpleBooleanEncodedValue conditional;

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> registerNewEncodedValue) {
        conditional = new SimpleBooleanEncodedValue("conditional");
        registerNewEncodedValue.add(conditional);
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay way, EncodingManager.Access access, IntsRef relationFlags) {
        List<TimeDependentAccessRestriction.ConditionalTagData> timeDependentAccessConditions = TimeDependentAccessRestriction.getTimeDependentAccessConditions(way);
        if (!timeDependentAccessConditions.isEmpty()) {
            conditional.setBool(false, edgeFlags, true);
        }
        return edgeFlags;
    }
}
