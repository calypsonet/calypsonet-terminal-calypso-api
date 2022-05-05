/* **************************************************************************************
 * Copyright (c) 2022 Calypso Networks Association https://calypsonet.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.calypsonet.terminal.calypso.transaction;

import org.calypsonet.terminal.calypso.spi.SamRevocationServiceSpi;

/**
 * Contains the input/output data of the {@link
 * CommonTransactionManager#prepareVerifySignature(SignatureVerificationData)} method for advanced
 * signature verification using the "PSO" command.
 *
 * @since 1.2.0
 */
public interface PsoSignatureVerificationData extends SignatureVerificationData {

  /**
   * Indicates that the signature has been computed in "SAM traceability" mode and therefore whether
   * the revocation status of the signing SAM should be checked or not.
   *
   * <p>By default, the signature is not supposed to have been computed in "SAM traceability" mode.
   *
   * @param offset The offset in bits of the SAM traceability data.
   * @param isPartialSamSerialNumber True if only the 3 LSBytes of the SAM serial number have been
   *     used.
   * @param checkSamRevocationStatus True if it is requested to check if the SAM is revoked or not.
   *     If true, then the {@link SamRevocationServiceSpi} service must be registered in the
   *     security settings using the {@link
   *     CommonSecuritySetting#setSamRevocationService(SamRevocationServiceSpi)} method.
   * @return The current instance.
   * @see PsoSignatureComputationData#withSamTraceabilityMode(int, boolean)
   * @see SamRevocationServiceSpi
   * @see CommonSecuritySetting#setSamRevocationService(SamRevocationServiceSpi)
   * @since 1.2.0
   */
  PsoSignatureVerificationData withSamTraceabilityMode(
      int offset, boolean isPartialSamSerialNumber, boolean checkSamRevocationStatus);

  /**
   * Indicates that the signature has been computed in non "Busy" mode.
   *
   * <p>By default, the signature is supposed to have been computed in "Busy" mode.
   *
   * <p>The signature may have been generated with "Busy mode" enabled. In this mode, after a "PSO
   * Verify Signature" failing because of an incorrect signature, during a few seconds the SAM
   * rejects any "PSO Verify Signature" commands with "Busy" mode by responding with the "busy"
   * status word.
   *
   * <p>When a "PSO Verify Signature" fails with the busy status, the terminal should repeat the
   * command until the SAM is not busy anymore.
   *
   * <p>The busy mode duration is typically of a few seconds, and it is never of greater than ten
   * seconds.
   *
   * <p>Note that after a reset of the SAM, "PSO Verify Signature" commands being in "Busy" mode
   * fail with the busy status until the end of the busy mode duration.
   *
   * @return The current instance.
   * @see PsoSignatureComputationData#withoutBusyMode()
   * @since 1.2.0
   */
  PsoSignatureVerificationData withoutBusyMode();
}
